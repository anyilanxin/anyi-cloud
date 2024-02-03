监控服务
===============

### 接入mprouthesjob配置，其中relabel_configs对标签进行替换

```
- job_name: 'anyicloud'
    metrics_path: '/actuator/prometheus'
    consul_sd_configs:
    - server: http://192.168.51.40:8086/monitor
      services: []
    relabel_configs:
      - source_labels: [__meta_consul_service_metadata_management_context_path]
        target_label: __metrics_path__
        replacement: ${1}/prometheus
      - regex: __meta_consul_service_metadata_(.+)
        action: labelmap
```
