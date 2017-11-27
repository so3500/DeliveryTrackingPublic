package xyz.dt.dtserver.service;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import domain.Delivery;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class DeliveryService {

    // DeliveryService 메모리에 Delivery 객체를 저장하는 로직
    private ConcurrentMap<Integer, Delivery> repo = new ConcurrentHashMap<>();

    // 배송 조회 이력 생성 후 리턴
    // 크롤링 로직
    public Delivery create(String invoiceNumber) throws IOException {

        Delivery delivery = null;
        String regex = "^[0-9]+$";  // 정수형만 체크, \\d+

        // 예외처리1: 송장번호(우체국 택배 기준)가 숫자로 이루어진 문자열이 아닌 경우, 빈 객체 리턴(result: -1: 잘못된 송장번호)
//        if (!invoiceNumber.matches(regex)){
//            delivery = new Delivery(invoiceNumber, "-", -1, "-", "-", "-",
//                    "-", "-", "-");
//        }
        // 예외처리2: 송장번호(우체국 택배 기준)가 13자리가 아닐 경우, 빈 객체 리턴(result: -1: 잘못된 송장번호)
        if (invoiceNumber.length() == 13) {
            // 크롤링 우체국 택배
            Document doc = Jsoup
                    .connect("https://service.epost.go.kr/trace.RetrieveDomRigiTraceList.comm?sid1=" + invoiceNumber + "&displayHeader=N")
                    .timeout(5000)
                    .get();

            // 파싱
            // 배송 진행상황: 날짜, 시간, 현재 상황, 현재 위치, 보내는 분, 받는 분
            Elements bodyTbody = doc.getElementsByClass("table_col detail_on ma_b_0 no-print").select("tbody");

            // 예외처리3: 올바른 송장번호 이지만 해당 내역이 존재하지 않는 경우, 빈 객체 리턴(result: -1: 잘못된 송장번호)
            if (bodyTbody.text().length() > 0){
                Elements bodyTrs = bodyTbody.select("tr");
                Elements bodyTds;

                String date = "", time = "", currentState = "", currentPosition = "", sender="", receiver="", lastPosition="";
                int result = 0;
                for (Element tr : bodyTrs) {
                    bodyTds = tr.select("td");
                    date += bodyTds.get(0).text().replace('.', '-') + "/";
                    time += bodyTds.get(1).text() + "/";
                    currentState += bodyTds.get(2).text() + "/";
                    currentPosition += bodyTds.get(3).text() + "/";
                    lastPosition = bodyTds.get(3).text();

                    if (lastPosition.equals("배달완료")) {
                        result = 1;
                    }
                }
                // 파싱
                // 배송 기본정보: 보내는 분, 받는 분
                Elements headTrs = doc.getElementsByClass("table_col").get(0).select("tbody").select("tr");
                Elements headTds;

                for (Element tr : headTrs) {
                    headTds = tr.select("td");
                    sender = headTds.get(0).text().split(" ")[0];
                    receiver = headTds.get(1).text().split(" ")[0];
                }

                // Delivery 객체 생성 및 저장 Delivery
                delivery = new Delivery(invoiceNumber, "우체국", result, date, time, currentState, currentPosition, sender, receiver);

            } else{
                // 예외처리3: 올바른 송장번호 이지만 해당 내역이 존재하지 않는 경우, 빈 객체 리던(result: -2 송장 내역 없음)
                delivery = new Delivery(invoiceNumber, "-", -2, "-", "-", "-",
                        "-", "-", "-");
            }
            // 결과를 DB에 저장
            // DAO 활용(보류)
            // ;
        } else {
            // 예외처리2: 송장번호(우체국 택배 기준)이 13자리가 아닐 경우, 빈 객체 리턴(result: -1 잘못된 길이의 송장 번호)
            delivery = new Delivery(invoiceNumber, "-", -1, "-", "-", "-",
                    "-", "-", "-");
        }
        // 컨트롤러로 객체 리턴
        return delivery;
    }
}
