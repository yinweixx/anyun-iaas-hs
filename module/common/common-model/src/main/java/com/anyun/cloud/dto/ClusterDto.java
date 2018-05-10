package com.anyun.cloud.dto;

import com.anyun.cloud.tools.db.AbstractEntity;

/**
 * Created by gp on 17-3-28.
 */
public class ClusterDto extends AbstractEntity {
    private String id;                  //集群主键
    private String name;                //区域名称
    private String description;         //描述
    private String type;                //类型 Docker
    private String status = "Enable";    //状态：Enable（可用）--默认 、Disable（禁用）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
