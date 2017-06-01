package me.ferdz.kittengarten.data;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;

import me.ferdz.kittengarten.model.Kitten;
import me.ferdz.kittengarten.service.client.IService;
import me.ferdz.kittengarten.util.RetrofitUtil;

/**
 * Created by 1452284 on 2016-08-26.
 */
public class Values {
    public static final Gson GSON = new Gson();
    public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
    public static final IService SERVICE = RetrofitUtil.getKittenService();
    public static MagicalCamera cam;
}
