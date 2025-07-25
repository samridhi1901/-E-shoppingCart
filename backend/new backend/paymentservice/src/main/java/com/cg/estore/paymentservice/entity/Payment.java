package com.cg.estore.paymentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "payments")
public class Payment {

	@Id
	private String paymentId;

	private double totalPrice;
	private String paymentMode;
	private String paymentStatus;


	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "card_number")
	private Card card;

	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;

	private String userName;
	private String cartId;

	@Transient
	private User userprofile;

}
