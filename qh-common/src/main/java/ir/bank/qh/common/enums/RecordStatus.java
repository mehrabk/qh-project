package ir.bank.qh.common.enums;

/** Generic active/inactive record lifecycle, used wherever the diagram/model carries a plain {@code RECORD_STATUS_CODE}/{@code RULE_STATUS_CODE}/{@code STATUS_CODE}/{@code PARAMETER_STATUS_CODE} column with no richer state machine. */
public enum RecordStatus {
    ACTIVE,
    INACTIVE
}
