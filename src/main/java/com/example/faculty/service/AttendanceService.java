package com.example.faculty.service;

import com.example.faculty.dto.Response;
import com.example.faculty.dto.attendance.AttendanceResponse;
import com.example.faculty.dto.attendance.PostAttendanceResponse;
import com.example.faculty.entity.Event;
import com.example.faculty.entity.EventAttendance;
import com.example.faculty.error.CustomException;
import com.example.faculty.repository.EventAttendanceRepository;
import com.example.faculty.repository.EventRepository;
import com.example.faculty.utils.ExcelExporter;
import com.example.faculty.utils.ExcelHelper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceService {

    @Autowired
    private EventAttendanceRepository eventAttendanceRepository;

    @Autowired
    private EventRepository eventRepository;

    public AttendanceResponse checkIn(String SID, String ID) {
        checkEvent(ID);

        Optional<EventAttendance> eventAttendance = eventAttendanceRepository.findByIdAndSid(ID, SID);
        if (!eventAttendance.isPresent()) {
            throw new CustomException("Can not find Student or Event!");
        }

        if (eventAttendance.get().getCheckIn() == 1) {
            throw new CustomException("You has been already checked in!");
        }

        EventAttendance newEventAttendance = eventAttendance.get();
        newEventAttendance.setCheckIn(1);
        eventAttendanceRepository.save(newEventAttendance);
        return AttendanceResponse
                .builder()
                .msg("Check in successfully!")
                .data(newEventAttendance)
                .build();
    }

    public AttendanceResponse checkOut(String SID, String ID) {
        checkEvent(ID);
        Optional<EventAttendance> eventAttendance = eventAttendanceRepository.findByIdAndSid(ID, SID);
        if (!eventAttendance.isPresent()) {
            throw new CustomException("Can not find Student or Event!");
        }

        if (eventAttendance.get().getCheckOut() == 1) {
            throw new CustomException("You has been already checked out!");
        }

        EventAttendance newEventAttendance = eventAttendance.get();
        newEventAttendance.setCheckOut(1);
        eventAttendanceRepository.save(newEventAttendance);
        return AttendanceResponse
                .builder()
                .msg("Check in successfully!")
                .data(newEventAttendance)
                .build();

    }

    public PostAttendanceResponse postEventAttendance(MultipartFile file, String ID) throws IOException {
        if (!ExcelHelper.hasExcelFormat(file)) {
            throw new CustomException("File format error!");
        }
        checkEvent(ID);

        Optional<List<EventAttendance>> eventAttendance = eventAttendanceRepository.findById(ID);
        if (eventAttendance.get().size() != 0) {
            throw new CustomException("Event attendances list has already been existed!");
        }

        List<EventAttendance> eventAttendanceList = ExcelHelper.excelToAttendance(file.getInputStream(), ID);
        eventAttendanceRepository.saveAll(eventAttendanceList);
        return new PostAttendanceResponse("Success!", ID, eventAttendanceList);
    }

    public PostAttendanceResponse getEventAttendanceList(String id) {
        checkEvent(id);
        return new PostAttendanceResponse("Success!", id, eventAttendanceRepository.findById(id).get());

    }

    public void exportExcel(HttpServletResponse response, String id) throws Exception {
        Event event = eventRepository.findById(id).get();
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=" + event.getName() + "_" + currentDateTime + ".xlsx";
        response.setHeader(headerkey, headervalue);

        List<EventAttendance> eventAttendanceList = eventAttendanceRepository.findById(id).get();
        ExcelExporter exporter = new ExcelExporter(eventAttendanceList);
        exporter.export(response);

    }

    public void deleteEventAttendance(String ID) {
        eventAttendanceRepository.deleteById(ID);
    }


    public void checkEvent(String ID) {
        Optional<Event> event = eventRepository.findById(ID);

        if (!event.isPresent()) {
            throw new CustomException("Event ID is not existed!");
        }

        Date date = new Date();

        if (date.before(new Date(event.get().getStartDate()))) {
            throw new CustomException("Event has not started!");
        }
        if (date.after(new Date(event.get().getEndDate()))) {
            throw new CustomException("Event has ended!");
        }
    }




}