package tobbit.movieproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tobbit.movieproject.model.Group;
import tobbit.movieproject.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group createGroup(String groupName, String createdBy, String description, ArrayList<String> members){
        Group group = new Group(groupName, description, createdBy, members);
        groupRepository.save(group);
        return group;
    }

    public List<Group> getUserGroup(String currentUser) {
        return groupRepository.findByCreatedBy(currentUser);
    }

}
