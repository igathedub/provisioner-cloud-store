import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';

type EntityResponseType = HttpResponse<IAllocatedUnicastRange>;
type EntityArrayResponseType = HttpResponse<IAllocatedUnicastRange[]>;

@Injectable({ providedIn: 'root' })
export class AllocatedUnicastRangeService {
  public resourceUrl = SERVER_API_URL + 'api/allocated-unicast-ranges';

  constructor(protected http: HttpClient) {}

  create(allocatedUnicastRange: IAllocatedUnicastRange): Observable<EntityResponseType> {
    return this.http.post<IAllocatedUnicastRange>(this.resourceUrl, allocatedUnicastRange, { observe: 'response' });
  }

  update(allocatedUnicastRange: IAllocatedUnicastRange): Observable<EntityResponseType> {
    return this.http.put<IAllocatedUnicastRange>(this.resourceUrl, allocatedUnicastRange, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAllocatedUnicastRange>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllocatedUnicastRange[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
