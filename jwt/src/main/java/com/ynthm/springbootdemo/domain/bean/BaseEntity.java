package com.ynthm.springbootdemo.domain.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 基类
 * 
 * @author Ynthm
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected long id;

	@CreationTimestamp
	protected Date createTime;

	@UpdateTimestamp
	protected Date updateTime;

}
