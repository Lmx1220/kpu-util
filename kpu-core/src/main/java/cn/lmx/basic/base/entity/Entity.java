package cn.lmx.basic.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lmx
 * @version 1.0
 * @description: 包括id、created_time、created_by、updated_by、update_time字段的表继承的基础实体
 * @date 2023/7/4 14:27
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Entity<T> extends SuperEntity<T> {

    public static final String UPDATED_TIME = "updatedTime";
    public static final String UPDATED_BY = "updatedBy";
    public static final String UPDATED_TIME_FIELD = "updated_time";
    public static final String UPDATED_BY_FIELD = "updated_by";
    private static final long serialVersionUID = 5169873634279173683L;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updatedTime;

    @ApiModelProperty(value = "最后修改人ID")
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    protected T updatedBy;

    public Entity(T id, LocalDateTime createdTime, T createdBy, LocalDateTime updatedTime, T updatedBy) {
        super(id, createdTime, createdBy);
        this.updatedTime = updatedTime;
        this.updatedBy = updatedBy;
    }

    public Entity() {
    }

}
