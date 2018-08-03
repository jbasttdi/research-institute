package com.heaven7.ve.kingdom;

import java.util.List;
import java.util.Objects;

/**
 * the kingdom data.
 * @author heaven7
 */
public class KingdomData {

    private List<MapData> maps;
    private List<String> subjects;
    private List<ModuleData> moduleDatas; // the configed module data

    public List<ModuleData> getModuleDatas() {
        return moduleDatas;
    }
    public void setModuleDatas(List<ModuleData> moduleDatas) {
        this.moduleDatas = moduleDatas;
    }

    public List<MapData> getMaps() {
        return maps;
    }
    public void setMaps(List<MapData> maps) {
        this.maps = maps;
    }

    public List<String> getSubjects() {
        return subjects;
    }
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public static class MapData {
        private int type;
        private List<TagItemData> itemDatas;

        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }

        public List<TagItemData> getItemDatas() {
            return itemDatas;
        }
        public void setItemDatas(List<TagItemData> itemDatas) {
            this.itemDatas = itemDatas;
        }
    }
    public static class TagItemData {
        private String tag;
        private List<TagItem> items;

        public String getTag() {
            return tag;
        }
        public void setTag(String tag) {
            this.tag = tag;
        }
        public List<TagItem> getItems() {
            return items;
        }
        public void setItems(List<TagItem> items) {
            this.items = items;
        }
    }

    /** the person proportion. which used to judge shot type when no person.
     * relative to a standard person.
     * */
    public static class PersonProportion{
        private String desc;
        private int index;//tag index
        private float proportion;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }

        public float getProportion() {
            return proportion;
        }
        public void setProportion(float proportion) {
            this.proportion = proportion;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PersonProportion that = (PersonProportion) o;
            return index == that.index;
        }

        @Override
        public int hashCode() {

            return Objects.hash(index);
        }
    }

    public static void main(String[] args) {
        
    }

}
    