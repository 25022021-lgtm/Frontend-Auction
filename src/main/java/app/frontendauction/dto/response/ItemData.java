package app.frontendauction.dto.response;

/**
 * DTO đại diện cho 1 item (phía client).
 * Khớp với các field mà backend Item entity serialize ra JSON.
 */
public class ItemData {
    private Long itemId;
    private String title;
    private String description;

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
