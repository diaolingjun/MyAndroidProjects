package com.jmd.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jmd.db.DBUtils;
import com.jmd.domain.BaseBean;
import com.jmd.domain.ExamBean;
import com.jmd.domain.UserBean;

public class ExamServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request--->" + request.getRequestURL() + "====" + request.getParameterMap().toString());
		response.setContentType("text/html;charset=utf-8");
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		// �����ݿ�����
		
		List<ExamBean> ebList=dbUtils.FindAll();
		// ��Gson���������л���json
		Gson gson = new Gson();
		// ������ת����json�ַ���
		String json = gson.toJson(ebList);
		try {
			response.getWriter().println(json);
			// ��json���ݴ����ͻ���
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // �ر����������Ȼ�ᷢ�������
		}
		dbUtils.closeConnect(); // �ر����ݿ�����}
	}
	}
