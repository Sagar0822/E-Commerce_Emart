package com.ECommerce.Emart.Service;

import com.ECommerce.Emart.Enum.ProductStatus;
import com.ECommerce.Emart.Exception.CustomerNotFoundException;
import com.ECommerce.Emart.Exception.ProductNotFoundException;
import com.ECommerce.Emart.Model.*;
import com.ECommerce.Emart.Repository.CartRepository;
import com.ECommerce.Emart.Repository.CustomerRepository;
import com.ECommerce.Emart.Repository.ProductRepository;
import com.ECommerce.Emart.RequestDto.OrderRequestDto;
import com.ECommerce.Emart.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JavaMailSender emailSender;

    public String addCart(OrderRequestDto orderRequestDto)throws Exception{
        Customer customer;
        try{
            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid customer Id");
        }

        Product product;
        try{
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        }
        catch(Exception e){
            throw new ProductNotFoundException("Invalid Product Id");
        }

        if(product.getQuantity() < orderRequestDto.getRequiredQty()){
            throw new Exception("Sorry! Required quentity not available");
        }
        Cart cart = customer.getCart();

        int newCost = cart.getCartTotal() + (product.getPrice() * orderRequestDto.getRequiredQty());
        cart.setCartTotal(newCost);

        // Add item to current cart
        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQty());
        item.setCart(cart);
        item.setProduct(product);
        cart.getItems().add(item);

        customerRepository.save(customer);

        return "Item has been added your cart";
    }

    //CheckOut - all item ordered in cart
    public List<OrderResponseDto> checkOut(int customerId)throws CustomerNotFoundException{
        Customer customer;
        try{
            customer  = customerRepository.findById(customerId).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid Customer Id");
        }

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        int totalCost  = customer.getCart().getCartTotal();
        Cart cart = customer.getCart();

        for(Item item : cart.getItems()){
            Ordered order = new Ordered();
            order.setTotalCost(item.getRequiredQuantity()*item.getProduct().getPrice());
            order.setDeliveryCharge(0);
            order.setCustomer(customer);
            order.getOrderedItems().add(item);

            //set Card for payment
            Card card = customer.getCards().get(0);
            String cardUsed = "";
            int cardLen = card.getCardNo().length();
            for(int i=0; i<cardLen-4; i++){
                cardUsed += 'X';
            }
            cardUsed += card.getCardNo().substring(cardLen - 4);
            order.setCardUsedForPayment(cardUsed);

            //Quantity update in warehouse
            int leftQuantity = item.getProduct().getQuantity() - item.getRequiredQuantity();
            if(leftQuantity <= 0)
                item.getProduct().setProductStatus(ProductStatus.OUT_OF_STOCK);

            item.getProduct().setQuantity(leftQuantity);

            customer.getOrders().add(order);

            //prepare response Dtos
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .productName(item.getProduct().getProductName())
                    .totalCost(order.getTotalCost())
                    .orderDate(order.getOrderDate())
                    .itemPrice(item.getProduct().getPrice())
                    .quantityOrdered(item.getRequiredQuantity())
                    .deliveryCharge(order.getDeliveryCharge())
                    .cardUsedForPayment(order.getCardUsedForPayment())
                    .build();

            orderResponseDtoList.add(orderResponseDto);
        }
//        String text = "Congrats your order with total value "+totalCost+" has been placed";
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("backenddeveloper02@gmail.com");
//        message.setTo(customer.getEmail());
//        message.setSubject("Order Placed Notification");
//        message.setText(text);
//        emailSender.send(message);

        cart.setItems(new ArrayList<>());
        cart.setCartTotal(0);
        customerRepository.save(customer);


        return orderResponseDtoList;
    }

}
