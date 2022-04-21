package br.senai.sp.cfp138.guideviagem.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.senai.sp.cfp138.guideviagem.annotation.Publico;

@Component
public class AppIntercetor implements HandlerInterceptor{

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// variavel para obter uri da request
		String uri = request.getRequestURI();
		// variavel para a session
		HttpSession session = request.getSession();
		
		if(uri.startsWith("/api")) {
			return true;
		} else {
		
			// verifica se handler è HandlerMethod, o que indica que esta chamando um metodo
			if(handler instanceof HandlerMethod) {
				//casting de Object para HandlerMethod
				HandlerMethod metodo = (HandlerMethod) handler;
				//verifica se é publico
				if(metodo.getMethodAnnotation(Publico.class) != null) {
					return true;
				}
				//verifica se existe um usuario na sessão
				if(session.getAttribute("usuarioLogado") != null) {
					return true;
				}
				//redireciona para pagina de login
				response.sendRedirect("/");
				return false;
			}
			
		}
		
		return true;
		
	}
	
}
