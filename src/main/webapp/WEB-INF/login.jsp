<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
	<form action="login.do" method="post">
		<fieldset>
			<legend>登陆</legend>
			用户名:<input name="username"/><br/>
			<span style="color:red;">
			${login_failed }
			</span><br/>
			密码:<input type="password" name="pwd"/><br/>
			验证码:<input name="number"/>
			<img id="img" alt="验证码" src="checkcode.do">
			<span style="color:red">
			${number_error }
			</span>
			<a href="javascript:;" 
			   onclick="document.getElementById('img').src='checkcode.do?'+Math.random();">看不清,换一个</a><br/>
			
			<input type="submit" value="确定"/>
		</fieldset>
	</form>
</body>
</html>