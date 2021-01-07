{{- define "buildrunenv" -}}
{{- range $name, $value := .env }}
  {{- if not (empty $value) }}
- name: {{ $name | quote }}
  value: {{ $value | quote }}
  {{- end }}
{{- end}}
{{- range $key0, $val0 := .application }}
  {{- if kindIs  "map" $val0  }}
    {{- range $key1, $val1 := $val0 }}
      {{- if kindIs  "map" $val1  }}
        {{- range $key2, $val2 := $val1 }}
          {{- if kindIs  "map" $val2  }}
            {{- range $key3, $val3 := $val2 }}
              {{- if kindIs  "map" $val3  }}
                {{- range $key4, $val4 := $val3 }}
                  {{- if kindIs  "map" $val4  }}
                    {{- range $key5, $val5 := $val4 }}
                      {{- if kindIs  "map" $val5  }}
                        {{- range $key6, $val6 := $val5 }}
                          {{- if kindIs  "map" $val6  }}
                            {{- range $key7, $val7 := $val6 }}
                              {{- if kindIs  "map" $val7  }}
                                {{- range $key8, $val8 := $val7 }}
                                  {{- if kindIs  "map" $val8  }}
                                    {{- range $key9, $val9 := $val8 }}
                                      {{- if kindIs  "map" $val9  }}
                                        {{- range $key10, $val10 := $val9 }}
                                          {{- if kindIs  "map" $val10  }}
                                            {{- range $key11, $val11 := $val10 }}
                                              {{- if kindIs  "map" $val11  }}
                                                {{- range $key12, $val12 := $val11 }}
                                                  {{- if kindIs  "map" $val12  }}
                                                    {{- range $key13, $val13 := $val12 }}
                                                      {{- if kindIs  "map" $val13  }}
                                                        {{- range $key14, $val14 := $val13 }}
                                                          {{- if kindIs  "map" $val14  }}
                                                            {{- range $key15, $val15 := $val14 }}
                                                              {{- if kindIs  "map" $val15  }}
                                                                {{- range $key16, $val16 := $val15 }}
                                                                  {{- if kindIs  "map" $val16  }}
                                                                    {{- range $key17, $val17 := $val16 }}
                                                                      {{- if kindIs  "map" $val17  }}
                                                                        {{- range $key18, $val18 := $val17 }}
                                                                          {{- if kindIs  "map" $val18  }}
                                                                            {{- range $key19, $val19 := $val18 }}
                                                                              {{- if kindIs  "map" $val19  }}
                                                                                {{- range $key20, $val20 := $val19 }}
                                                                                  {{- if kindIs  "map" $val20  }}
                                                                                    {{- range $key21, $val21 := $val20 }}
                                                                                      {{- if kindIs  "map" $val21  }}
                                                                                        {{- range $key22, $val22 := $val21 }}
                                                                                          {{- if kindIs  "map" $val22  }}
                                                                                            {{- range $key23, $val23 := $val22 }}
                                                                                              {{- if kindIs  "map" $val23  }}
                                                                                                {{- range $key24, $val24 := $val23 }}
                                                                                                  {{- if kindIs  "map" $val24  }}

                                                                                                  {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}_{{ $key16 | upper | replace `-` `_` }}_{{ $key17 | upper | replace `-` `_` }}_{{ $key18 | upper | replace `-` `_` }}_{{ $key19 | upper | replace `-` `_` }}_{{ $key20 | upper | replace `-` `_` }}_{{ $key21 | upper | replace `-` `_` }}_{{ $key22 | upper | replace `-` `_` }}_{{ $key23 | upper | replace `-` `_` }}_{{ $key24 | upper | replace `-` `_` }}
  value: {{ $val24 | quote }}
                                                                                                  {{- end }}
                                                                                                {{- end }}
                                                                                              {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}_{{ $key16 | upper | replace `-` `_` }}_{{ $key17 | upper | replace `-` `_` }}_{{ $key18 | upper | replace `-` `_` }}_{{ $key19 | upper | replace `-` `_` }}_{{ $key20 | upper | replace `-` `_` }}_{{ $key21 | upper | replace `-` `_` }}_{{ $key22 | upper | replace `-` `_` }}_{{ $key23 | upper | replace `-` `_` }}
  value: {{ $val23 | quote }}
                                                                                              {{- end }}
                                                                                            {{- end }}
                                                                                          {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}_{{ $key16 | upper | replace `-` `_` }}_{{ $key17 | upper | replace `-` `_` }}_{{ $key18 | upper | replace `-` `_` }}_{{ $key19 | upper | replace `-` `_` }}_{{ $key20 | upper | replace `-` `_` }}_{{ $key21 | upper | replace `-` `_` }}_{{ $key22 | upper | replace `-` `_` }}
  value: {{ $val22 | quote }}
                                                                                          {{- end }}
                                                                                        {{- end }}
                                                                                      {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}_{{ $key16 | upper | replace `-` `_` }}_{{ $key17 | upper | replace `-` `_` }}_{{ $key18 | upper | replace `-` `_` }}_{{ $key19 | upper | replace `-` `_` }}_{{ $key20 | upper | replace `-` `_` }}_{{ $key21 | upper | replace `-` `_` }}
  value: {{ $val21 | quote }}
                                                                                      {{- end }}
                                                                                    {{- end }}
                                                                                  {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}_{{ $key16 | upper | replace `-` `_` }}_{{ $key17 | upper | replace `-` `_` }}_{{ $key18 | upper | replace `-` `_` }}_{{ $key19 | upper | replace `-` `_` }}_{{ $key20 | upper | replace `-` `_` }}
  value: {{ $val20 | quote }}
                                                                                  {{- end }}
                                                                                {{- end }}
                                                                              {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}_{{ $key16 | upper | replace `-` `_` }}_{{ $key17 | upper | replace `-` `_` }}_{{ $key18 | upper | replace `-` `_` }}_{{ $key19 | upper | replace `-` `_` }}
  value: {{ $val19 | quote }}
                                                                              {{- end }}
                                                                            {{- end }}
                                                                          {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}_{{ $key16 | upper | replace `-` `_` }}_{{ $key17 | upper | replace `-` `_` }}_{{ $key18 | upper | replace `-` `_` }}
  value: {{ $val18 | quote }}
                                                                          {{- end }}
                                                                        {{- end }}
                                                                      {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}_{{ $key16 | upper | replace `-` `_` }}_{{ $key17 | upper | replace `-` `_` }}
  value: {{ $val17 | quote }}
                                                                      {{- end }}
                                                                    {{- end }}
                                                                  {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}_{{ $key16 | upper | replace `-` `_` }}
  value: {{ $val16 | quote }}
                                                                  {{- end }}
                                                                {{- end }}
                                                              {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}_{{ $key15 | upper | replace `-` `_` }}
  value: {{ $val15 | quote }}
                                                              {{- end }}
                                                            {{- end }}
                                                          {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}_{{ $key14 | upper | replace `-` `_` }}
  value: {{ $val14 | quote }}
                                                          {{- end }}
                                                        {{- end }}
                                                      {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}_{{ $key13 | upper | replace `-` `_` }}
  value: {{ $val13 | quote }}
                                                      {{- end }}
                                                    {{- end }}
                                                  {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}_{{ $key12 | upper | replace `-` `_` }}
  value: {{ $val12 | quote }}
                                                  {{- end }}
                                                {{- end }}
                                              {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}_{{ $key11 | upper | replace `-` `_` }}
  value: {{ $val11 | quote }}
                                              {{- end }}
                                            {{- end }}
                                          {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}_{{ $key10 | upper | replace `-` `_` }}
  value: {{ $val10 | quote }}
                                          {{- end }}
                                        {{- end }}
                                      {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}_{{ $key9 | upper | replace `-` `_` }}
  value: {{ $val9 | quote }}
                                      {{- end }}
                                    {{- end }}
                                  {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}_{{ $key8 | upper | replace `-` `_` }}
  value: {{ $val8 | quote }}
                                  {{- end }}
                                {{- end }}
                              {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}_{{ $key7 | upper | replace `-` `_` }}
  value: {{ $val7 | quote }}
                              {{- end }}
                            {{- end }}
                          {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}_{{ $key6 | upper | replace `-` `_` }}
  value: {{ $val6 | quote }}
                          {{- end }}
                        {{- end }}
                      {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}_{{ $key5 | upper | replace `-` `_` }}
  value: {{ $val5 | quote }}
                      {{- end }}
                    {{- end }}
                  {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}_{{ $key4 | upper | replace `-` `_` }}
  value: {{ $val4 | quote }}
                  {{- end }}
                {{- end }}
              {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}_{{ $key3 | upper | replace `-` `_` }}
  value: {{ $val3 | quote }}
              {{- end }}
            {{- end }}
          {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}_{{ $key2 | upper | replace `-` `_` }}
  value: {{ $val2 | quote }}
          {{- end }}
        {{- end }}
      {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}_{{ $key1 | upper | replace `-` `_` }}
  value: {{ $val1 | quote }}
      {{- end }}
    {{- end }}
  {{- else }}
- name: {{ $key0 | upper | replace `-` `_`}}
  value: {{ $val0 | quote }}
  {{- end }}
{{- end }}
{{- end}}