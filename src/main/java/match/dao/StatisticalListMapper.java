package match.dao;

import java.util.List;
import match.dao.model.StatisticalList;
import match.dao.model.StatisticalListExample;
import org.apache.ibatis.annotations.Param;

public interface StatisticalListMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    long countByExample(StatisticalListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    int deleteByExample(StatisticalListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    int insert(StatisticalList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    int insertSelective(StatisticalList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    List<StatisticalList> selectByExample(StatisticalListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    StatisticalList selectByPrimaryKey(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") StatisticalList record, @Param("example") StatisticalListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") StatisticalList record, @Param("example") StatisticalListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(StatisticalList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistical_list
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(StatisticalList record);
}