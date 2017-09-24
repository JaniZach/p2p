package com.xmg.p2p.base.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 基本domain
 * @author Jani
 */
@Setter@Getter
public abstract class BaseDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
}
