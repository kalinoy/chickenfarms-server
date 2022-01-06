package com.chickenfarms.chickenfarms.model.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CustomerInTicketPKId implements Serializable {
    
    private long ticketId;
    
    private long customerId;
    
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object==null){
            return false;
        }
        if(object instanceof CustomerInTicketPKId){
            CustomerInTicketPKId customerInTicketPKId= (CustomerInTicketPKId)object;
            if(customerInTicketPKId.getTicketId()==this.getTicketId() && customerInTicketPKId.getCustomerId()==this.getCustomerId()){
                return true;
            }
        }
        return false;
    }
    
    @Override 
    public int hashCode(){
        final int prime=31;
        int result=1;
        result= (int) (prime*result+ticketId);
        result=prime*result+(int)customerId;
        return result;
    }
    
}
