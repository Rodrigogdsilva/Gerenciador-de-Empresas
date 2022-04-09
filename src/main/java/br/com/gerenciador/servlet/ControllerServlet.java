package br.com.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.gerenciador.acoes.Acao;


@WebServlet("/entrada")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String parametroAcao = request.getParameter("acao");

		String form;
		try {
			Class classe = Class.forName("br.com.gerenciador.acoes." + parametroAcao);
			Acao acao = (Acao) classe.newInstance();
			form = acao.executa(request, response);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new ServletException(e);
		}

		String[] tipo = form.split(":");

		if (tipo[0].equals("forward")) {
			request.getRequestDispatcher("WEB-INF/view/" + tipo[1]).forward(request, response);
		} else {
			response.sendRedirect(tipo[1]);
		}
	}

}
