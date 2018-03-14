package com.xinshen.attendancesystem.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.main.employee.EmployeeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2017/12/6.
 */

public class EmployeeUtils {

    public static List<EmployeeBean> getEmployees(Context context){
        List<EmployeeBean> employees;
        Gson gson = new Gson();
        String json = (String) SPUtils.get(context, Global.Const.KEY_EMPLOYEE,"NULL");
        if (!"NULL".equals(json)){
            employees = gson.fromJson(json,
                    new TypeToken<List<EmployeeBean>>(){}.getType());
            return employees;
        }else{
            employees = new ArrayList<>();
        }
        return  employees;
    }

    public static void saveEmployee(Context context,EmployeeBean employee){
        if (employee == null)
            return;
        List<EmployeeBean> employees = getEmployees(context);
        employees.add(employee);
        Gson gson = new Gson();
        SPUtils.put(context,Global.Const.KEY_EMPLOYEE,gson.toJson(employees));
    }

    public static void setEmployee(Context context,EmployeeBean employee,int index){
        if (employee == null)
            return;
        ArrayList<EmployeeBean> employees = (ArrayList<EmployeeBean>) getEmployees(context);
        employees.set(index,employee);
        Gson gson = new Gson();
        SPUtils.put(context,Global.Const.KEY_EMPLOYEE,gson.toJson(employees));
    }
}
