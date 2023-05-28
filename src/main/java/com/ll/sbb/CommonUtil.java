package com.ll.sbb;

import org.springframework.stereotype.Component;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
    public String markdown(String markdown) {
        Parser parser = Parser.builder().build();
        //Parser 객체를 구성하기 위한 빌더(Builder)를 반환
        Node document = parser.parse(markdown);
        //markdown 문자열을 파서하고 분석된 markdown 문서를 나타내는 node 객체 반환
        //document는 파싱된 Markdown 문서의 루트 노드를 나타냅니다
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        //HtmlRenderer.builder()는 HtmlRenderer 객체를 구성하기 위한 빌더를 반환
        //기본 빌더를 사용하여 HtmlRenderer 객체를 생성하고 있으므로 추가적인 설정이나 구성 없이
        //기본 렌더러를 생성하는 것
        //기본 렌더러는 Markdown 문서를 HTML로 변환하는 기능을 제공
        return renderer.render(document);
    }
}
