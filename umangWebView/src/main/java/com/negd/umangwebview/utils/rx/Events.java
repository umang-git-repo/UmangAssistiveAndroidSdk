package com.negd.umangwebview.utils.rx;


public class Events {

    public Events() {
    }

    public static class DigiLoginEvent {

    }

    public static class AllServiceEvent {

    }

    public static class DigiLogoutEvent {

    }

    public static class FragmentLoaded{

    }

    public static class DatabaseChangeEvent {

    }

    public static class StateChangeEvent {

    }

//    public static class HomeCategoryClickEvent {
//        public Category category;
//
//        public HomeCategoryClickEvent(Category category) {
//            this.category = category;
//        }
//
//    }

    public static class ServiceOpenEvent{

        public String serviceId;
        public String actionUrl;

        public ServiceOpenEvent(String serviceId, String actionUrl) {
            this.serviceId = serviceId;
            this.actionUrl = actionUrl;
        }
    }

}
