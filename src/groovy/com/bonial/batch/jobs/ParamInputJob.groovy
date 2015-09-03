package com.bonial.batch.jobs

import com.bonial.batch.Name
import com.bonial.batch.item.NameItem
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.LineMapper

class FileLineMapper implements LineMapper<NameItem> {

    @Override
    NameItem mapLine(String s, int i) throws Exception {
        NameItem item = new NameItem()
        String[] tokens = s.split(/,/)
        if(tokens.length == 1)
            item.lastName = tokens[0]
        else if(tokens.length == 2) {
            item.lastName = tokens[0]
            item.firstName = tokens[1]
        }
        return item
    }

}

class NameItemProcessor implements ItemProcessor<NameItem, Name> {

    @Override
    Name process(NameItem nameItem) throws Exception {
        Name name = new Name()
        name.with {
            firstName = nameItem.firstName
            lastName = nameItem.lastName
            completeName = nameItem.toString()
        }
        return name
    }

}

class NameItemWriter implements ItemWriter<Name> {

    def batchTestService

    @Override
    void write(List<? extends Name> list) throws Exception {
        batchTestService.saveNames(list)
    }

}