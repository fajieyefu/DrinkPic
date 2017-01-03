package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import net.sf.json.*;

import com.db.DBManager;

/*
 * ����servlet��������ݿ�ľ������
 */
public class Service {

	public String getWaterInfo(String cardId, int type, int count) {

		long times = System.currentTimeMillis();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat2 =new SimpleDateFormat("yyyy-MM");
		String date2=dateFormat2.format(times);
		String date =dateFormat.format(times);
		String waterInfoSql = null;
		int pages_start = (count - 1) * 10;
		// ��ȡsql��ѯ���
		if (type == 0) {
			waterInfoSql = "select * from ac_event where event_card='" + cardId
					+ "' and  CAST(event_time as TEXT) ~'" + date
					+ "'and remark !='' order by event_time desc";
		} else if (count == 1) {
			waterInfoSql = "select * from ac_event where event_card='"
					+ cardId + "'and remark !='' order by event_time desc limit 10";
		} else {
			waterInfoSql = "select * from ac_event where event_time not in"
					+ "(select  event_time from ac_event where event_card='"
					+ cardId+ "'and remark !='' order by event_time desc limit '"+pages_start+"')"
					+ " and event_card='"
					+ cardId + "'and remark !='' order by event_time desc limit 10";
		}
		System.out.println(waterInfoSql);
		// ��ȡDB����
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ResultSet rs = sql.executeQuery(waterInfoSql);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		int num_1=0; //����intֵ����ס���պ�ˮ����
		int num_2=0;//����int,��ס���º�ˮ����
		String data="����Ϊ��";
		try {
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					String drink_time = rs.getString("event_time");// ��ȡ��ˮʱ��
					String drink_picture = rs.getString("remark");// ��ȡ��ˮ��Ƭ
					jsonObject = new JSONObject();
					jsonObject.put("drink_pic", drink_picture);
					jsonObject.put("drink_time", drink_time);
					jsonArray.add(jsonObject);
					num_1++;
				}
				data=jsonArray.toString();
			}
		} catch (SQLException e) {
			data="���ݿⷱæ";
		}
		if(type==0){
			waterInfoSql = "select * from ac_event where event_card='" + cardId
					+ "' and CAST(event_time as TEXT) like'" + date2
					+ "' order by event_time desc";
			ResultSet rs2 = sql.executeQuery(waterInfoSql);
			try {
				if(rs2.next()){
					rs2.previous();
					while(rs2.next()){
						num_2++;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				data="���ݿⷱæ";
			}
		}
		sql.closeDB();
		jsonObject= new JSONObject();
		jsonObject.put("num_1", num_1);
		jsonObject.put("num_2",num_2);
		jsonObject.put("data", data);
		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}

	// ��ȡȡֵΪnullʱ�����ַ�����ֵnotEdit
	private String nullToString(String string) {
		if (string == null) {
			return "null";
		} else {
			return string;
		}
	}
}
