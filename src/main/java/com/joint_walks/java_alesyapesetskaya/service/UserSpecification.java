package com.joint_walks.java_alesyapesetskaya.service;



//@Service
//public class UserSpecification {
//
//    public static Specification<User> getUserSpecification(UpdateRequest request) {
//        return (root, query, criteriaBuilder) -> {
//            String ownerName = request.getOwnerName();
//            Integer ownerAge = request.getOwnerAge();
//            String dogName = request.getDogName();
//            String dogType = request.getDogType();
//            Integer dogAge = request.getDogAge();
//
//            List<Predicate> predicateList = new ArrayList<>();
//            if (ownerName != null) {
//                Predicate ownerNamePredicate = criteriaBuilder.equal(root.get("ownerName"), ownerName);
//                predicateList.add(ownerNamePredicate);
//            }
//            if (ownerAge != null) {
//                Predicate ownerAgePredicate = criteriaBuilder.equal(root.get("ownerAge"), ownerAge);
//                predicateList.add(ownerAgePredicate);
//            }
//            if (dogName != null) {
//                Predicate dogNamePredicate = criteriaBuilder.equal(root.get("dogName"), dogName);
//                predicateList.add(dogNamePredicate);
//            }
//            if (dogType != null) {
//                Predicate dogTypePredicate = criteriaBuilder.equal(root.get("dogType"), dogType);
//                predicateList.add(dogTypePredicate);
//            }
//            if (dogAge != null) {
//                Predicate dogAgePredicate = criteriaBuilder.equal(root.get("dogAge"), dogAge);
//                predicateList.add(dogAgePredicate);
//            }
//            return criteriaBuilder.and(predicateList.toArray(Predicate[]::new));
//        };
//    }
//}

