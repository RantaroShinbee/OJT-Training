package com.customers.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String _id;

	@Size(min = 2, max = 30)
	@NotNull
	private String name;

	@CreatedDate
	@Column(name = "create_at", nullable = false)
	private Date createAt;

	@Column(name = "update_at")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date updateAt;

	@Value("${some.key:1}")
	@Column(name = "status", nullable = false)
	private int status;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Customer [_id=" + _id + ", name=" + name + ", createAt=" + createAt + ", updateAt=" + updateAt
				+ ", status=" + status + "]";
	}

}
