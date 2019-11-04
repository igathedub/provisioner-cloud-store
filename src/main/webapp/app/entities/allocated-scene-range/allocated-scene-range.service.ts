import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';

type EntityResponseType = HttpResponse<IAllocatedSceneRange>;
type EntityArrayResponseType = HttpResponse<IAllocatedSceneRange[]>;

@Injectable({ providedIn: 'root' })
export class AllocatedSceneRangeService {
  public resourceUrl = SERVER_API_URL + 'api/allocated-scene-ranges';

  constructor(protected http: HttpClient) {}

  create(allocatedSceneRange: IAllocatedSceneRange): Observable<EntityResponseType> {
    return this.http.post<IAllocatedSceneRange>(this.resourceUrl, allocatedSceneRange, { observe: 'response' });
  }

  update(allocatedSceneRange: IAllocatedSceneRange): Observable<EntityResponseType> {
    return this.http.put<IAllocatedSceneRange>(this.resourceUrl, allocatedSceneRange, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAllocatedSceneRange>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllocatedSceneRange[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
