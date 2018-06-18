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
	//�����֤��ͼƬ
	public void checkcode(HttpServletRequest request,HttpServletResponse response) throws IOException {
		/**
		 * 1.����ͼƬ
		 */
		//��������
		BufferedImage image=new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);
		//��û���
		Graphics g=image.getGraphics();
		//����������ɫ
		g.setColor(new Color(255,255,255));
		//���û�����ɫ
		g.fillRect(0, 0, 80, 30);
		//���¸������������ɫ
		Random r=new Random();
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		//��������
		g.setFont(new Font(null,Font.ITALIC,24));
		//����һ�������
		String number=getNumber(4);
		//����֤��󶨵�session������
		HttpSession session=request.getSession();
		session.setAttribute("number", number);
		//��ͼƬ�ϻ��������
		g.drawString(number, 5, 25);
		//��Ӹ�����
		for(int i=0;i<10;i++) {
			g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
			g.drawLine(r.nextInt(80), r.nextInt(30), r.nextInt(80), r.nextInt(30));
		}
		/**
		 * 2.ѹ��ͼƬ�����
		 */
		//��������������������ص���һ��ͼƬ
		response.setContentType("image/jpeg");
		//����ֽ������
		OutputStream output=response.getOutputStream();
		//��ԭʼͼƬ����ָ����ʽjepg����ѹ���������
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
			throw new ApplicationException("��֤�����");
		}
		//������ַ���ҵ���������
		User user=ls.checkLogin(username,pwd);
		//��½�ɹ�����user����󶨵�session������
		session.setAttribute("user", user);
		//��¼�ɹ�
		return "redirect:toIndex.do";
	}
	
	@RequestMapping("/toIndex.do")
	public String toIndex() {
		return "index";
	}
}
