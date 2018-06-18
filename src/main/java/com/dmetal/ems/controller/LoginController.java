package com.dmetal.ems.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dmetal.ems.entity.User;
import com.dmetal.ems.service.ApplicationException;
import com.dmetal.ems.service.LoginService;

@Controller
public class LoginController {
	@Resource(name="LoginService")
	private LoginService ls;
	
	@ExceptionHandler
	public String handle(Exception e,HttpServletRequest request) {
		System.out.println("handle()");
		if(e instanceof ApplicationException) {
			request.setAttribute("login_failed", e.getMessage());
			return "login";
		}
		return "error";
	}
	@RequestMapping("/checkcode.do")
	//输出验证码图片
	public void checkcode(HttpServletRequest request,HttpServletResponse response) throws IOException {
		/**
		 * 1.生成图片
		 */
		//创建画布
		BufferedImage image=new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);
		//获得画笔
		Graphics g=image.getGraphics();
		//给笔设置颜色
		g.setColor(new Color(255,255,255));
		//设置画布颜色
		g.fillRect(0, 0, 80, 30);
		//重新给笔设置随机颜色
		Random r=new Random();
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		//设置字体
		g.setFont(new Font(null,Font.ITALIC,24));
		//生成一个随机数
		String number=getNumber(4);
		//将验证码绑定到session对象上
		HttpSession session=request.getSession();
		session.setAttribute("number", number);
		//在图片上绘制随机数
		g.drawString(number, 5, 25);
		//添加干扰线
		for(int i=0;i<10;i++) {
			g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
			g.drawLine(r.nextInt(80), r.nextInt(30), r.nextInt(80), r.nextInt(30));
		}
		/**
		 * 2.压缩图片并输出
		 */
		//告诉浏览器，服务器返回的是一张图片
		response.setContentType("image/jpeg");
		//获得字节输出流
		OutputStream output=response.getOutputStream();
		//将原始图片按照指定格式jepg进行压缩，输出。
		javax.imageio.ImageIO.write(image, "jpeg", output);
		output.close();
	}
	
	private String getNumber(int size) {
		String chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789";
		String number="";
		Random r=new Random();
		for(int i=0;i<size;i++) {
			number+=chars.charAt(r.nextInt(chars.length()));
		}
		return number;
	}
	
	
	@RequestMapping("/toLogin.do")
	public String toLogin() {
		System.out.println("toLogin()");
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request,HttpSession session) {
		System.out.println("login()");
		String number=request.getParameter("number");
		String username=request.getParameter("username");
		String pwd=request.getParameter("pwd");
		System.out.println(username+" "+pwd+" "+number);
		Object obj=session.getAttribute("number");
		if(!obj.equals(number)) {
			throw new ApplicationException("验证码错误");
		}
		//将请求分发给业务层来处理
		User user=ls.checkLogin(username,pwd);
		//登陆成功，将user对象绑定到session对象上
		session.setAttribute("user", user);
		//登录成功
		return "redirect:toIndex.do";
	}
	
	@RequestMapping("/toIndex.do")
	public String toIndex() {
		return "index";
	}
}
