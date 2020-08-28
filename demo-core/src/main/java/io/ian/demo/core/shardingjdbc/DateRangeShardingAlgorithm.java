package io.ian.demo.core.shardingjdbc;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.*;

@Slf4j
public class DateRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
        List<String> list = new ArrayList<>();
        log.info("availableTargetNames : " + availableTargetNames);
        log.info(rangeShardingValue.toString());
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        Date lowerDate = valueRange.lowerEndpoint();
        Date upperDate = valueRange.upperEndpoint();
        String lowerSuffix = ShardingUtils.getSuffixByYearMonth(lowerDate);
        String upperSuffix = ShardingUtils.getSuffixByYearMonth(upperDate);
        TreeSet<String> suffixList = ShardingUtils.getSuffixListForRange(lowerSuffix, upperSuffix);
        for (String tableName : availableTargetNames) {
            if (containTableName(suffixList, tableName)) {
                list.add(tableName);
            }
        }
        log.info("match tableNames-----------------------" + list.toString());
        return list;
    }

    private boolean containTableName(Set<String> suffixList, String tableName) {
        boolean flag = false;
        for (String s : suffixList) {
            if (tableName.endsWith(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
