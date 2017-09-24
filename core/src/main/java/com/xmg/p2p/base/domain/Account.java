package com.xmg.p2p.base.domain;

import java.math.BigDecimal;

import com.xmg.p2p.base.util.BidConst;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装用户的账户信息
 * @author Jani
 */
@Setter@Getter
public class Account extends BaseDomain{
	private int version;//对象版本信息,乐观锁相关
	private String tradePassword;//交易密码
	private BigDecimal usableAmount = BidConst.ZERO;//账户可用余额
	private BigDecimal freezedAmount = BidConst.ZERO;//账户冻结金额
	private BigDecimal unReceiveInterest=BidConst.ZERO;//账户待收利息
	private BigDecimal unReceivePrincipal=BidConst.ZERO;//账户待收本金
	private BigDecimal unReturnAmount=BidConst.ZERO;//账户待还金额
	private BigDecimal remainBorrowLimit=BidConst.INIT_BORROW_LIMIT;//账户剩余授信额度
	private BigDecimal borrowLimit=BidConst.INIT_BORROW_LIMIT;//账户授信额度
	//获取总金额=账户可以用余额+账户冻结金额+账户待收利息+账户待收本金
	public BigDecimal getTotalAmount(){
		return this.usableAmount
				.add(this.freezedAmount)
				.add(this.unReceiveInterest)
				.add(this.unReceivePrincipal);
	}
}
