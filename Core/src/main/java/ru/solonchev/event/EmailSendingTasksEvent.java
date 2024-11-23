package ru.solonchev.event;

import java.util.List;

public record EmailSendingTasksEvent(String email, String headerOfLetter, List<String> finished, List<String> active) {

    @Override
    public String toString() {
        return "EmailSendingTasksEvent{" +
               "email='" + email + '\'' +
               ", headerOfLetter='" + headerOfLetter + '\'' +
               ", finished=" + finished +
               ", active=" + active +
               '}';
    }
}
