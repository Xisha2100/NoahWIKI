package top.nzhz.wiki.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.nzhz.wiki.domain.MusicSnapshot;
import top.nzhz.wiki.domain.MusicSnapshotExample;

public interface MusicSnapshotMapper {
    long countByExample(MusicSnapshotExample example);

    int deleteByExample(MusicSnapshotExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MusicSnapshot record);

    int insertSelective(MusicSnapshot record);

    List<MusicSnapshot> selectByExample(MusicSnapshotExample example);

    MusicSnapshot selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MusicSnapshot record, @Param("example") MusicSnapshotExample example);

    int updateByExample(@Param("record") MusicSnapshot record, @Param("example") MusicSnapshotExample example);

    int updateByPrimaryKeySelective(MusicSnapshot record);

    int updateByPrimaryKey(MusicSnapshot record);
}