package com.fxy.dhm.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色
 * @TableName se_role
 */
@TableName(value ="se_role")
@Data
public class SeRole implements Serializable {
    /**
     * 
     */
    @TableField(value = "role_id")
    private Long role_id;

    /**
     * 
     */
    @TableField(value = "role_name")
    private String role_name;

    /**
     * 
     */
    @TableField(value = "role_key")
    private String role_key;

    /**
     * 
     */
    @TableField(value = "dataScope")
    private Integer dataScope;

    /**
     * 
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SeRole other = (SeRole) that;
        return (this.getRole_id() == null ? other.getRole_id() == null : this.getRole_id().equals(other.getRole_id()))
            && (this.getRole_name() == null ? other.getRole_name() == null : this.getRole_name().equals(other.getRole_name()))
            && (this.getRole_key() == null ? other.getRole_key() == null : this.getRole_key().equals(other.getRole_key()))
            && (this.getDataScope() == null ? other.getDataScope() == null : this.getDataScope().equals(other.getDataScope()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRole_id() == null) ? 0 : getRole_id().hashCode());
        result = prime * result + ((getRole_name() == null) ? 0 : getRole_name().hashCode());
        result = prime * result + ((getRole_key() == null) ? 0 : getRole_key().hashCode());
        result = prime * result + ((getDataScope() == null) ? 0 : getDataScope().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", role_id=").append(role_id);
        sb.append(", role_name=").append(role_name);
        sb.append(", role_key=").append(role_key);
        sb.append(", dataScope=").append(dataScope);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}