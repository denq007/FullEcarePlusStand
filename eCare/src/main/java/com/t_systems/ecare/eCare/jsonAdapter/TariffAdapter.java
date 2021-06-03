package com.t_systems.ecare.eCare.jsonAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.t_systems.ecare.eCare.entity.Tariff;

import java.io.IOException;

public class TariffAdapter extends TypeAdapter<Tariff> {

    @Override
    public void write(JsonWriter writer, Tariff tariff) throws IOException {
        writer.beginObject();
        writer.name("name").value(tariff.getName());
        writer.name("price").value(tariff.getPrice());
        writer.endArray();
        writer.endObject();
        writer.close();
    }

    @Override
    public Tariff read(JsonReader reader) throws IOException {
        return null;
    }

}
