package cn.lmx.basic.uid.dao;

import com.baidu.fsg.uid.worker.entity.WorkerNodeEntity;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @author lmx
 * @version 1.0
 * @description: DAO for WORKER_NODE
 * @date 2023/7/4 14:27
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface WorkerNodeDao {

    /**
     * Add {@link WorkerNodeEntity}
     *
     * @param workerNodeEntity
     */
    @Insert("INSERT INTO worker_node( host_name,port, type, launch_date,modified,created) " +
            "VALUES (#{hostName},#{port},#{type},#{launchDate},#{modified}, #{created})")
    // oracle 用下面2个注解！ mysql sql server 用上面1个注解！
//    @Insert(databaseId = "oracle", value = "INSERT INTO worker_node(id, host_name,port, type, launch_date,modified,created) " +
//            "VALUES (#{id}, #{hostName},#{port},#{type},#{launchDate},#{modified}, #{created})")
//    @SelectKey(databaseId = "oracle", statement = "SELECT WORKER_NODE_SEQ.NEXTVAL as id FROM DUAL", keyColumn = "id",
//            keyProperty = "id", resultType = long.class, before = true)
    void addWorkerNode(WorkerNodeEntity workerNodeEntity);

}