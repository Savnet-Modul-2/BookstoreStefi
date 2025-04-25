package com.example.SpringBookstore;

public enum ReservationStatus {
    PENDING {
        public boolean isNextStatusValid(ReservationStatus nextStatus) {
            return nextStatus == ReservationStatus.IN_PROGRESS;
        }
    },
    IN_PROGRESS {
        public boolean isNextStatusValid(ReservationStatus nextStatus) {
            return nextStatus == ReservationStatus.DELAYED || nextStatus == ReservationStatus.FINISHED;
        }
    },
    DELAYED {
        public boolean isNextStatusValid(ReservationStatus nextStatus) {
            return nextStatus == ReservationStatus.FINISHED;
        }
    },
    CANCELLED {
        public boolean isNextStatusValid(ReservationStatus nextStatus) {
            return false;
        }
    },
    FINISHED {
        public boolean isNextStatusValid(ReservationStatus nextStatus) {
            return false;
        }
    };

    public abstract boolean isNextStatusValid(ReservationStatus reservationStatus);
}
