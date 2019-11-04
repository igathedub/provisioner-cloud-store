import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProvisioner } from 'app/shared/model/provisioner.model';

type EntityResponseType = HttpResponse<IProvisioner>;
type EntityArrayResponseType = HttpResponse<IProvisioner[]>;

@Injectable({ providedIn: 'root' })
export class ProvisionerService {
  public resourceUrl = SERVER_API_URL + 'api/provisioners';

  constructor(protected http: HttpClient) {}

  create(provisioner: IProvisioner): Observable<EntityResponseType> {
    return this.http.post<IProvisioner>(this.resourceUrl, provisioner, { observe: 'response' });
  }

  update(provisioner: IProvisioner): Observable<EntityResponseType> {
    return this.http.put<IProvisioner>(this.resourceUrl, provisioner, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProvisioner>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProvisioner[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
