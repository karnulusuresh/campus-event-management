package com.campus.event.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.campus.event.constant.RoleEnum;
import com.campus.event.dto.EventDTO;
import com.campus.event.entity.Category;
import com.campus.event.entity.Event;
import com.campus.event.entity.User;
import com.campus.event.exception.AccessDeniedException;
import com.campus.event.exception.BadRequestException;
import com.campus.event.exception.ConflictException;
import com.campus.event.exception.GeneralServiceException;
import com.campus.event.exception.ResourceNotFoundException;
import com.campus.event.pojo.EventRequest;
import com.campus.event.repository.CategoryRepository;
import com.campus.event.repository.EventRepository;
import com.campus.event.repository.UserRepository;
import com.campus.event.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // --------------------------------------------------------
    // 1. Get All Events
    // --------------------------------------------------------
    @Override
    public List<EventDTO> getAllEvents() {
        try {
            log.info("Fetching all events...");
            List<Event> events = eventRepository.findAll();
            if (events.isEmpty()) {
                throw new ResourceNotFoundException("No events found");
            }
            return events.stream().map(EventServiceImpl::mapToDTO).toList();
        } catch (Exception ex) {
            log.error("Error retrieving all events: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to retrieve events", ex);
        }
    }

    // --------------------------------------------------------
    //2. Get Event By ID
    // --------------------------------------------------------
    @Override
    public EventDTO getEventById(Long id) {
        try {
            log.info("Retrieving event by ID: {}", id);
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID " + id));
            return mapToDTO(event);
        } catch (ResourceNotFoundException ex) {
            throw ex; // handled globally
        } catch (Exception ex) {
            log.error("Unexpected error fetching event by ID {}: {}", id, ex.getMessage());
            throw new GeneralServiceException("Failed to fetch event details", ex);
        }
    }

    // --------------------------------------------------------
    //3. Get Events By Category
    // --------------------------------------------------------
    @Override
    public List<EventDTO> getEventsByCategory(Long categoryId) {
        try {
            log.info("Retrieving events for categoryId: {}", categoryId);
            List<Event> events = eventRepository.findByCategory_CategoryId(categoryId);
            if (events.isEmpty()) {
                throw new ResourceNotFoundException("No events found for category ID " + categoryId);
            }
            return events.stream().map(EventServiceImpl::mapToDTO).toList();
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error retrieving events by category: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to fetch events by category", ex);
        }
    }

    // --------------------------------------------------------
    //4. Create Event (with role-based access validation)
    // --------------------------------------------------------
    @Override
    public EventDTO createEvent(EventRequest eventRequest) {
        try {
            log.info("Event creation started: {}", eventRequest.getTitle());

            // Validate category
            Category category = categoryRepository.findById(eventRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + eventRequest.getCategoryId()));

            // Validate user
            User createdUser = userRepository.findById(eventRequest.getCreatedByUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + eventRequest.getCreatedByUserId()));

            // Role check
            if (!createdUser.getUserRole().equals(RoleEnum.ADMIN)) {
                throw new AccessDeniedException("Only admins can create events.");
            }

            // Validate logical event times
            if (eventRequest.getEndDateTime().isBefore(eventRequest.getStartDateTime())) {
                throw new BadRequestException("End time cannot be before start time.");
            }

            // Save event
            Event event = Event.builder()
                    .title(eventRequest.getTitle())
                    .description(eventRequest.getDescription())
                    .startDateTime(eventRequest.getStartDateTime())
                    .endDateTime(eventRequest.getEndDateTime())
                    .location(eventRequest.getLocation())
                    .category(category)
                    .createdByUser(createdUser)
                    .build();

            Event savedEvent = eventRepository.save(event);
            log.info("Event created successfully with ID: {}", savedEvent.getId());

            return mapToDTO(savedEvent);

        } catch (AccessDeniedException | ResourceNotFoundException | BadRequestException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Event creation failed due to data conflict: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error creating event: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to create event", ex);
        }
    }

    // --------------------------------------------------------
    // 5. Update Event
    // --------------------------------------------------------
    @Override
    public EventDTO updateEvent(Long id, EventRequest request) {
        try {
            log.info("Updating event with ID: {}", id);

            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID " + id));

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + request.getCategoryId()));

            User user = userRepository.findById(request.getCreatedByUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + request.getCreatedByUserId()));

            // Only admin can update
            if (!user.getUserRole().equals(RoleEnum.ADMIN)) {
                throw new AccessDeniedException("Only admins can update events.");
            }

            if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
                throw new BadRequestException("End time cannot be before start time.");
            }

            event.setTitle(request.getTitle());
            event.setDescription(request.getDescription());
            event.setStartDateTime(request.getStartDateTime());
            event.setEndDateTime(request.getEndDateTime());
            event.setLocation(request.getLocation());
            event.setCategory(category);
            event.setCreatedByUser(user);

            Event updatedEvent = eventRepository.save(event);
            log.info("Event updated successfully with ID: {}", updatedEvent.getId());
            return mapToDTO(updatedEvent);

        } catch (AccessDeniedException | ResourceNotFoundException | BadRequestException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Event update failed due to data conflict: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error updating event: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to update event", ex);
        }
    }

    // --------------------------------------------------------
    // 6. Delete Event By ID
    // --------------------------------------------------------
    @Override
    public String deleteEvent(Long id) {
        try {
            log.info("Deleting event with ID: {}", id);
            if (!eventRepository.existsById(id)) {
                throw new ResourceNotFoundException("Event not found with ID " + id);
            }
            eventRepository.deleteById(id);
            return "Event deleted successfully.";
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error deleting event: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to delete event", ex);
        }
    }

    // --------------------------------------------------------
    // 7. Delete All Events
    // --------------------------------------------------------
    @Override
    public String deleteAllEvents() {
        try {
            log.info("Deleting all events...");
            eventRepository.deleteAll();
            return "All events deleted successfully.";
        } catch (Exception ex) {
            log.error("Error deleting all events: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to delete all events", ex);
        }
    }

    // --------------------------------------------------------
    // Mapping (Entity → DTO)
    // --------------------------------------------------------
    public static EventDTO mapToDTO(Event event) {
        if (event == null) return null;

        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setStartDateTime(event.getStartDateTime());
        dto.setEndDateTime(event.getEndDateTime());
        dto.setLocation(event.getLocation());
        dto.setCategoryName(event.getCategory() != null ? event.getCategory().getName() : null);
        dto.setCreatedByUserId(event.getCreatedByUser() != null ? event.getCreatedByUser().getUserId() : null);
        return dto;
    }
}
