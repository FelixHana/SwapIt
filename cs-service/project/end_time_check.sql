create definer = root@localhost event project.end_time_check on schedule
    every '1' MINUTE
        starts '2023-12-27 14:19:34'
    on completion preserve
    enable
    do
    update request set request_state = 3 where end_time <= NOW() and request_state = 0;

