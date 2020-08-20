package io.ian.demo.core.shardingjdbc;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DateRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {
    private Logger logger = LoggerFactory.getLogger(DateRangeShardingAlgorithm.class);

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
        List<String> list = new ArrayList<>();
        logger.info("availableTargetNames : " + availableTargetNames);
        logger.info(rangeShardingValue.toString());
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
        logger.info("match tableNames-----------------------" + list.toString());
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
