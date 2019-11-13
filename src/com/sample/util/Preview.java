package com.sample.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sample.model.Board;

public class Preview {
	
	// 리스트에서 글 미리보기
	public static void setPreviewContent(List<Board> boards) {
		for(Board board : boards) {
			String content = board.getContent();
			
			Document doc = Jsoup.parse(content);
			
			board.setContent("<p>" + doc.text() + "</p>");
		}
	}
	
	// 리스트에서 이미지 미리보기
	public static void setPreviewImg(List<Board> boards) {
		for(Board board : boards) {
			String content = board.getContent();
			
			// jsoup로 컨텐츠를 파싱해서 첫번째 이미지를 가져옴
			Document doc = Jsoup.parse(content);
			Element e = doc.selectFirst("img");

			if(e != null) {
				// 이미지의 src를 board의 previewImg에 저장
				String previewImg = e.attr("src");
				board.setPreviewImg(previewImg);
			} else {
				// 이미지가 없는 경우 a태그를 찾음
				Elements ets = doc.select("a");
				
				// a 태그가 있을 때
				if(ets != null) {
					String thumbnail = "";
					
					// a 태그 중 href가 youtube인 것을 찾아서 있는 경우 thumbnail에 해당 동영상 섬네일 저장
					for(Element el : ets) {
						String href = el.attr("href");
						if(href.contains("https://www.youtube.com/watch")) {
							String url[] = href.split("=");
							String videoUrl = url[1];
							thumbnail = "http://i.ytimg.com/vi/" + videoUrl + "/0.jpg";
							break;
						}
					}
					
					// 섬네일에 저장된 내용이 있으면 previewImg에 담고 아닐 경우 기본 이미지를 세팅
					if(!thumbnail.equals("")) {
						board.setPreviewImg(thumbnail);
					} else {
						board.setPreviewImg("/blogNew/bootstrap/images/img_2.jpg");
					}
				}
			}
		}
	}

	// 유튜브 동영상 미리보기
	public static void setPreviewYoutube(Board board) {
		String content = board.getContent();
		
		// jsoup로 컨텐츠를 파싱해서 a태그를 찾음
		Document doc = Jsoup.parse(content);
		Elements ets = doc.select("a");
		
		// a태그가 있는 경우 youtube url 다시 찾음
		if(ets != null) {
			for(Element e : ets) {
				String href = e.attr("href");
				
				// youtube url이 있는 경우
				if(href.contains("https://www.youtube.com/watch")) {
					String url[] = href.split("=");
					String videoUrl = url[1];
	
					// iframe으로 youtube 동영상을 띄워줌
					String iframe = "<iframe src='https://www.youtube.com/embed/" + videoUrl + "' width='600px' height='350px' allowFullScreen='true' />";
					
					e.after(iframe);
				}
			}
			
			board.setContent(doc.toString());
		}
	}
}
