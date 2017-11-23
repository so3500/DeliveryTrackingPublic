package xyz.dt.dtserver.presentation;

import domain.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.dt.dtserver.service.DeliveryService;

import java.io.IOException;
import java.util.Collection;

@RestController
public class DeliveryController {

    private final DeliveryService service;

    @Autowired
    public DeliveryController(DeliveryService service){
        this.service = service;
    }

//     기존의 배송 조회 리스트 리턴
//    @GetMapping("/delivery-tracking")
//    public Delivery readDeliveryTemplate(){
//        return service.findById("1");
//    }

    // 배송 조회 생성 후 생성된 객체 리턴
    @GetMapping("/delivery-tracking")
    Delivery create(@RequestParam("invoiceNumber") String invoiceNumber) throws IOException{
//        System.out.println(invoiceNumber);
//        return service.findById(invoiceNumber);
        return service.create(invoiceNumber);
    }
}
