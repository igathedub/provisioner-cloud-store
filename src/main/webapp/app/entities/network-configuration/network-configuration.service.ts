import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

type EntityResponseType = HttpResponse<INetworkConfiguration>;
type EntityArrayResponseType = HttpResponse<INetworkConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class NetworkConfigurationService {
  public resourceUrl = SERVER_API_URL + 'api/network-configurations';

  constructor(protected http: HttpClient) {}

  create(networkConfiguration: INetworkConfiguration): Observable<EntityResponseType> {
    return this.http.post<INetworkConfiguration>(this.resourceUrl, networkConfiguration, { observe: 'response' });
  }

  update(networkConfiguration: INetworkConfiguration): Observable<EntityResponseType> {
    return this.http.put<INetworkConfiguration>(this.resourceUrl, networkConfiguration, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INetworkConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INetworkConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
