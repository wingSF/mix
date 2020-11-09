package com.wing.lynne.po.temp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OneLayerCheckItem extends BaseCheckItem {

    private int id;

    private String name;

    private String userName;

    private int pid;

}
