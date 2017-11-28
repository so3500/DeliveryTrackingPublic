package xyz.dt.dtserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DtServerApplication.class)
@WebAppConfiguration
//@SpringBootTest
public class DeliveryControllerTest {

    @Autowired
    WebApplicationContext wac;
    MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(this.wac)
                .alwaysDo(print(System.out))
                .build();
    }

    @Test
    public void shouldCreate() throws Exception {
        Random random = new Random();

        char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int numberOfTries = 10;
        int numberIndex = 0;

        // 송장번호 example 
        StringBuilder invoiceNumber = new StringBuilder("6863225220200");           // 13자리 송장 번호
        StringBuilder invoiceNumberLonger = new StringBuilder("6863225220200000");  // 13자리 이상 송장 번호
        StringBuilder invoiceNumberShorter = new StringBuilder("68220200");        // 13자리 이하 송장 번호
        int invoiceNumberLen = invoiceNumber.length();
        int invoiceNumberLongerLen = invoiceNumberLonger.length();
        int invoiceNumberShorterLen = invoiceNumberShorter.length();

        for (int i=0; i<numberOfTries; i++){
            // 13자리 송장 번호에 대한 테스트
            // expect result: -2(배송내역 없음) or 0(배송중) or 1(배송완료)
            mvc.perform(
                    get("/delivery-tracking")
                        .param("invoiceNumber", invoiceNumber.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").exists());
//                    .andExpect(jsonPath("$.result").value(1))
//                    .andExpect(jsonPath("$.result").value(0))
//                    .andExpect(jsonPath("$.result").value(-2));

            // 13자리 이상의 송장 번호에 대한 테스트
            // expected result: -1(잘못된 송장번호)
            mvc.perform(
                    get("/delivery-tracking")
                            .param("invoiceNumber", invoiceNumberLonger.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(-1));

            // 13자리 이하의 송장 번호에 대한 테스트
            // expected result: -1(잘못된 송장번호)
            mvc.perform(
                    get("/delivery-tracking")
                            .param("invoiceNumber", invoiceNumberShorter.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(-1));

            numberIndex = random.nextInt(10); // 0 ~ 9
            invoiceNumber.setCharAt(random.nextInt(invoiceNumberLen), numbers[numberIndex]);
            invoiceNumberLonger.setCharAt(random.nextInt(invoiceNumberLongerLen), numbers[numberIndex]);
            invoiceNumberShorter.setCharAt(random.nextInt(invoiceNumberShorterLen), numbers[numberIndex]);
        }
    }
}
