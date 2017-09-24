package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 后台实名认证的控制器
 *
 * @author Jani
 */
@Controller
public class RealAuthController {
    @Autowired
    private IRealAuthService realAuthService;

    /**
     * 后台实名认证列表
     *
     * @return
     */
    @RequestMapping("realAuth")
    public String realAuthList(@ModelAttribute("qo") RealAuthQueryObject qo, Model model) {
        PageResult pageResult = this.realAuthService.queryPage(qo);
        model.addAttribute("pageResult", pageResult);
        return "realAuth/list";
    }

    /**
     * 后台实名认证的审核方法
     *
     * @param id     认证对象id
     * @param state  认证对象审核状态
     * @param remark 认证对象审核备注
     * @return
     */
    @RequestMapping("realAuth_audit")
    @ResponseBody
    public AjaxResult realAuthAudit(Long id, int state, String remark) {
        AjaxResult result = null;
        try {
            //调用业务方法进行审核操作
            this.realAuthService.audit(id, state, remark);
            result = new AjaxResult(true, "审核完成!");
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(e.getMessage());
        }
        return result;
    }
}
