package com.ECommerce.Emart.Service;

import com.ECommerce.Emart.Enum.ProductStatus;
import com.ECommerce.Emart.Exception.CustomerNotFoundException;
import com.ECommerce.Emart.Exception.ProductNotFoundException;
import com.ECommerce.Emart.Model.*;
import com.ECommerce.Emart.Repository.CustomerRepository;
import com.ECommerce.Emart.Repository.OrderedRepository;
import com.ECommerce.Emart.Repository.ProductRepository;
import com.ECommerce.Emart.RequestDto.OrderRequestDto;
import com.ECommerce.Emart.ResponseDTO.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Service
@Slf4j
public class OrderService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private OrderedRepository orderedRepository;
    JavaMailSender emailSender;
    public OrderResponseDto orderPlace(OrderRequestDto orderRequestDto) throws Exception{

        Customer customer;
        try{
            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid Customer Id !");
        }

        Product product;
        try{
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        }
        catch(Exception e){
            throw new ProductNotFoundException("Invalid Product Id !");
        }

        if(product.getQuantity() < orderRequestDto.getRequiredQty()){
            throw new Exception("Sorry !! Required quantity not Available");
        }

        Ordered order = new Ordered();

        order.setTotalCost(orderRequestDto.getRequiredQty()* product.getPrice());
        order.setDeliveryCharge(50);

        //prepare the card string
        Card card = customer.getCards().get(0);
        String cardUsed = "";
        int cardNo = card.getCardNo().length();
        for(int i = 0; i < cardNo-4; i++){
            cardUsed += 'X';
        }
        cardUsed += card.getCardNo().substring(cardNo-4);
        order.setCardUsedForPayment(cardUsed);

        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQty());
        item.setProduct(product);
        item.setOrder(order);

        order.getOrderedItems().add(item);
        order.setCustomer(customer);

        //update the quantity left in wareHouse
        int leftQuantity = product.getQuantity() - orderRequestDto.getRequiredQty();

        if(leftQuantity <= 0) {
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
            product.setQuantity(leftQuantity);


        customer.getOrders().add(order);
        Customer savedCustomer = customerRepository.save(customer);
        Ordered savedOrder = savedCustomer.getOrders().get(savedCustomer.getOrders().size()-1);

        //prepare response Dto
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .productName(product.getProductName())
                .orderDate(savedOrder.getOrderDate())
                .totalCost(order.getTotalCost())
                .itemPrice(product.getPrice())
                .quantityOrdered(orderRequestDto.getRequiredQty())
                .deliveryCharge(50)
                .cardUsedForPayment(order.getCardUsedForPayment())
                .build();

        // send an email
//        String text = "Congrats your order with total value "+order.getTotalCost()+" has been placed";
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("backenddeveloper02@gmail.com");
//        message.setTo(customer.getEmail());
//        message.setSubject("Order Placed Notification");
//        message.setText(text);
//        emailSender.send(message);

        return orderResponseDto;

    }
}
