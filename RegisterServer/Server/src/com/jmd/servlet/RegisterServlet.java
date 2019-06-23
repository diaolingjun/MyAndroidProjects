package com.jmd.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jmd.db.DBUtils;
import com.jmd.domain.BaseBean;
import com.jmd.domain.UserBean;

public class RegisterServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request--->" + request.getRequestURL() + "====" + request.getParameterMap().toString());
		String username = request.getParameter("username"); // ��ȡ�ͻ��˴������Ĳ���
		String password = request.getParameter("password");
		response.setContentType("text/html;charset=utf-8");
		if (username==null||username.equals("")||password.equals("")|| password==null) {
			System.out.println("�û���������Ϊ��");
		}else {
			// �������ݿ�
		 
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		// �����ݿ�����
		BaseBean basebean = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		UserBean userBean = new UserBean(); // user�Ķ���
		if (dbUtils.isExistInDB(username, password)) {
			// �ж��˺��Ƿ����
			basebean.setCode(-1);
			basebean.setData(userBean);
			basebean.setMsg("���˺��Ѵ���");
		} else if (!dbUtils.insertDataToDB(username, password)) {
			// ע��ɹ�
			basebean.setCode(0);
			basebean.setMsg("ע��ɹ�!!");
			ResultSet rs = dbUtils.getUser();
			int id = -1;
			if (rs != null) {
				try {
					while (rs.next()) {
						if (rs.getString("user_name").equals(username) && rs.getString("user_pwd").equals(password)) {
							id = rs.getInt("user_id");
						}
					}
					userBean.setId(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			userBean.setUsername(username);
			userBean.setPassword(password);
			basebean.setData(userBean);
		} else {
			// ע�᲻�ɹ����������û��ϸ�֣�����Ϊ���ݿ����
			basebean.setCode(500);
			basebean.setData(userBean);
			basebean.setMsg("���ݿ����");
		}
		// ��Gson���������л���json
		Gson gson = new Gson();
		// ������ת����json�ַ���
		String json = gson.toJson(basebean);
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

}