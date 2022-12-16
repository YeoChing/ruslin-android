pub struct FFINote {
    pub id: String,
    pub parent_id: Option<String>,
    pub title: String,
    pub body: String,
    pub created_time: i64,
    pub updated_time: i64,
    pub is_conflict: bool,
    pub latitude: f64,
    pub longitude: f64,
    pub altitude: f64,
    pub author: String,
    pub source_url: String,
    pub is_todo: bool,
    pub todo_due: bool,
    pub todo_completed: bool,
    pub source: String,
    pub source_application: String,
    pub application_data: String,
    pub order: i64,
    pub user_created_time: i64,
    pub user_updated_time: i64,
    pub encryption_cipher_text: String,
    pub encryption_applied: bool,
    pub markup_language: bool,
    pub is_shared: bool,
    pub share_id: String,
    pub conflict_original_id: Option<String>,
    pub master_key_id: String,
}
