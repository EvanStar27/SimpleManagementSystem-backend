package io.github.managementsystem.managementsystem.Enclosures;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnclosureRowMapper implements RowMapper<Enclosure> {

    @Override
    public Enclosure mapRow(ResultSet rs, int rowNum) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> fileTypes = new ArrayList<>();

        try {
            fileTypes = mapper.readValue(rs.getString("file_types"),
                    new TypeReference<List<String>>() {
                    });
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Enclosure.builder()
                .enclosureId(rs.getBigDecimal("enclosure_id").toBigInteger())
                .enclosureName(rs.getString("enclosure_name"))
                .required(rs.getBoolean("required"))
                .fileTypes(fileTypes)
                .fileSize(rs.getLong("file_size"))
                .type(rs.getString("type"))
                .build();
    }
}
