package com.spring.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {

	@Id
	@GeneratedValue
	private Integer id;
	private String firstName;
	private String middleName;
	private String lastName;
    private Long customerNumber;
    private String status;
    
    //@Temporal(TemporalType.TIME)
    private Date createDateTime;
   // @Temporal(TemporalType.TIME)
    private Date updateDateTime;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Address customerAddress;
    @OneToOne(cascade = CascadeType.ALL)
    private Contact contactDetails;
}
